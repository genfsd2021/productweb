//Doing a Product web app, in product page to 
//display the name, description, imageUrl, style, price, ..., ...,.....,....


const createHTMLList = (index, name, description, imageURL) =>
`
<div class="col-lg-4">
<div class="card" style="width: 18rem;">
    <img src=${imageURL} class="card-img-top"
        alt="image">
    <div class="card-body">
        <h5 class="card-title">${name}</h5>
        <p class="card-text">${description}</p>
        <a id="${index}" href="#" class="btn btn-primary" data-toggle="modal" data-target="#productModal">More</a>
    </div>
</div>
</div>

`;


function displayProductDetail(item)
{
    document.querySelector("#modalName").innerText = item.oName;
    document.querySelector("#modalImg").src = item.oImageUrl;
    document.querySelector("#modalStyle").innerText = item.oStyle;
    document.querySelector("#modalPrice").innerText = item.oPrice;

}


class ProductsController 
{
    constructor()
    {
        this._items = [];       //create an array to store the details of product items
    }

    //method to add the items into the array
    addItem(name, description, imageUrl, style, price, imagePath)
    {
        var productController = this;

        const item = {
         name: name,
         description: description,
         imageUrl: imageUrl,
         style: style,
         price: price
        };

        // Push the item to the items property
        //productController._items.push(item);
         const formData = new FormData();
         formData.append('name', name);
         formData.append('description', description);
         formData.append('imageUrl', imageUrl);
         formData.append('style', style);
         formData.append('price', price);
         formData.append('price', price);
         formData.append('imagefile', imagePath);

         //fetch('http://localhost:8080/item/add', {
         fetch('https://productwebfinal.herokuapp.com/item/add', {
             method: 'POST', // or 'PUT'
             /*headers: {
                 //'content-type': 'undefined'

                 //'mimeType': 'application/octet-stream'
             },*/
             body: formData
             })
             .then(response => response.json())
             .then(data => {
                 console.log('Success:', data);
                 alert("Successfully added to Product")
                 //this.displayItem();         //To display in the table from ProductForm.html
             })
             .catch((error) => {
                 console.error('Error:', error);
                 alert("Error adding item to Product")
             });
    }

    displayItem()
    {
        //fetch data from database using the REST API endpoint from Spring Boot
         var productController = this;
         productController._items = [];

        //fetch('http://127.0.0.1:8080/item/all')
        fetch('https://productwebfinal.herokuapp.com/item/all')
            .then((resp) => resp.json())
            .then(function(data) {
                console.log("2. receive data")
                console.log(data);

                /*data.forEach(function (item, index) {

                    const itemObj = {
                        oId: item.id,
                        oName: item.name,
                        oDescription: item.description,
                        oImageUrl: item.imageUrl,
                        oStyle: item.style,
                        oPrice: item.price
                   };

                productController._items.push(itemObj);*/
              //});

              //productController.render();

            })
            .catch(function(error) {
                console.log(error);
            });
    }

    render()
    {
        var productController = this;
        const productHTMLList = [];

         //Display products after getting from the database
         for (var i=0; i< productController._items.length; i++)
         {
             const item = productController._items[i];

             //var loc = window.location.href;
             //var full = item.oImageUrl.pathname;

             const productHTML = createHTMLList(i, item.oName, item.oDescription,  item.oImageUrl);
             productHTMLList.push(productHTML)
         }

         const pHtml = productHTMLList.join('\n');
         const itemsContainer = document.querySelector('#row');
         itemsContainer.innerHTML = pHtml;

         for (let i=0; i< productController._items.length; i++)
         {
             const item = productController._items[i];
             document.getElementById(i).addEventListener("click", function() { displayProductDetail(item);});
         }
    }
}   //End of ProductsController class
