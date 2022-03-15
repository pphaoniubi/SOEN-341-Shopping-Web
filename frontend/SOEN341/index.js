const button = document.getElementById('searchButton');

button.addEventListener('click', async _ => {
  try {     
    const response = await fetch('https://405c-206-176-145-172.ngrok.io/customer/shoppingCart', {
      method: 'get',
      body: {
        // Your body
      }
    });
    console.log('Completed!', response);
  } catch(err) {
    console.error(`Error: ${err}`);
  }
});