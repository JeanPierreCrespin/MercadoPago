// Agregar credenciales de SDK
//SUSTITUYA CON SU LLAVE PÚBLICA DISPONIBLE EN: https://developers.mercadopago.com/panel
const mercadopago = new MercadoPago('PUBLIC_KEY', {
  locale: 'es-AR' // The most common are: 'pt-BR', 'es-AR' and 'en-US'
});

// Maneja la llamada al backend y genera preferencia.
document.getElementById("checkout-btn").addEventListener("click", function() {

  $('#checkout-btn').attr("disabled", true);
  
  const orderData = {
    p_name: document.getElementById("produc-name").innerHTML,
    quantity: document.getElementById("quantity").value,
    img_url: document.getElementById("url1").innerHTML,
    description: document.getElementById("product-description").innerHTML,
    price: document.getElementById("unit-price").innerHTML
  };
    
  fetch("/pago/create_preference", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
    },
    body: JSON.stringify(orderData),
  })
    .then(function(response) {
        return response.json();
    })
    .then(function(response) {
        console.log("preference::"+response.id);
        createCheckoutButton(response.id);
        
        $(".shopping-cart").fadeOut(500);
        setTimeout(() => {
            $(".container_payment").show(500).fadeIn();
        }, 500);
    })
    .catch(function() {
        alert("Unexpected error");
        $('#checkout-btn').attr("disabled", false);
    });
});

// Crear preferencia al hacer clic en el botón de pago
function createCheckoutButton(preferenceId) {
  // Initialize the checkout
  mercadopago.checkout({
    preference: {
      id: preferenceId
    },
    render: {
      container: '#button-checkout', // Class name where the payment button will be displayed
      label: 'Pagar la compra', // Change the payment button text (optional)
    }
  });
}

// Handle price update
function updatePrice() {
  let quantity = document.getElementById("quantity").value;
  let unitPrice = document.getElementById("unit-price").innerHTML;
  let amount = parseInt(unitPrice) * parseInt(quantity);

  document.getElementById("cart-total").innerHTML = "$ " + amount;
  document.getElementById("summary-price").innerHTML = "$ " + unitPrice;
  document.getElementById("summary-quantity").innerHTML = quantity;
  document.getElementById("summary-total").innerHTML = "$ " + amount;
}

document.getElementById("quantity").addEventListener("change", updatePrice);
updatePrice();  

// Regresa
document.getElementById("go-back").addEventListener("click", function() {
  $(".container_payment").fadeOut(500);
  setTimeout(() => {
      $(".shopping-cart").show(500).fadeIn();
  }, 500);
  $('#checkout-btn').attr("disabled", false);  
});