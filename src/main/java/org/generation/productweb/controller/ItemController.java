package org.generation.productweb.controller;

import org.generation.productweb.repository.Entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;

import org.generation.productweb.Service.ItemService;
import org.generation.productweb.controller.dto.ItemDTO;
import org.springframework.web.multipart.MultipartFile;
import org.generation.productweb.repository.ItemRepository;
import java.io.*;
import java.util.*;

import org.generation.productweb.component.FileUploadUtil;

@RestController
@RequestMapping("/item")
public class ItemController {

    final ItemService itemService;

    /*final ItemRepository itemRepository;

    public ItemController(@Autowired ItemRepository itemRepository )
    {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public Iterable<Item> getItems(){
        return itemRepository.findAll();
    }*/

    public ItemController( @Autowired ItemService itemService )
    {
        this.itemService = itemService;
    }

    @CrossOrigin
    @GetMapping( "/all" )
    public Iterable<Item> getItems()
    {
        return itemService.all();
    }

    /*
    update the handler method that is responsible to handle form submission
    To handle file uploaded from the client, we need to declare this parameter for the handler method:
    @RequestParam("image") MultipartFile multipartFile
    Just use the spring starter web dependency is enough. Under the hood, Spring will use Apache Commons File Upload
    that parses multipart request to reads data from the file uploaded.
    */

    //To avoid CORS
    @CrossOrigin
    @PostMapping("/add")
    public Item save(  @RequestParam(name="name", required = true) String name,
                       @RequestParam(name="description", required = true) String description,
                       @RequestParam(name="imageUrl", required = true) String imageUrl,
                       @RequestParam(name="style", required = true) String style,
                       @RequestParam(name="price", required = true) double price,
                       @RequestParam("imagefile") MultipartFile multipartFile) throws IOException {

        String uploadDir1 = "productImages/images";
        //String uploadDir2 = "build/resources/main/static/images";

        //Next, we get the name of the uploaded file
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        //The class StringUtils come from the package org.springframework.util.
        // Note that we store only the file name in the database table,
        // and the actual uploaded file is stored in the file system:
        //Here, the uploaded file is stored in the directory productImages/images,
        // which is relative to the application’s directory.
        FileUploadUtil.saveFile(uploadDir1, fileName, multipartFile);

        ItemDTO itemDto = new ItemDTO(name, description, imageUrl, style, price);
        return itemService.save(new Item(itemDto));
    }

    @CrossOrigin
    @GetMapping( "/{id}" )
    public Item findItemById( @PathVariable Integer id )
    {
        return itemService.findById( id );
    }

    @CrossOrigin
    @PutMapping( "/{id}" )
    public Item update( @PathVariable Integer id, @RequestBody ItemDTO itemDto )
    //public Item update( @PathVariable Integer id )
    {
        System.out.println("Hello");
        Item item = itemService.findById( id );
        item.setName( itemDto.getName() );
        item.setDescription( itemDto.getDescription() );
        item.setImageUrl( itemDto.getImageUrl() );
        item.setStyle( itemDto.getStyle());
        item.setPrice( itemDto.getPrice());
        return itemService.save( item );
    }

    @CrossOrigin
    @DeleteMapping( "/{id}" )
    public void delete( @PathVariable Integer id )
    {
        itemService.delete( id );
    }

}
