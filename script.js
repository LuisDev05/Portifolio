let menulcon = document.querySelector('#menu-icon');
let sections = document.querySelectorAll('section');
let navLinks =  document.querySelectorAll('header nav a');

// Get the menu icon and navbar elements
const menuIcon = document.getElementById('#menu-icon');
const navbar = document.querySelector('.navbar');

// Add an event listener to the menu icon
menuIcon.addEventListener('click', () => {
  // Toggle the active class on the navbar
  navbar.classList.toggle('active');
});
window.onscroll = () => {
    sections.forEach(sec =>  {
        let top = window.scrollY;
        let offset = sec.offsetTop - 150;
        let height = sec.offsetHeight;
        let id = sec.Attribute('id');
    
        if(top >= offset && top < offset + height){
            navLinks.forEach(links => {
                links.classList.renove('active');
                document.querySelector('header nav a [href*=' + id +']').classList.add('active');
            })
        }
    })
}
menulcon.onclick = () => {
    menulcon.classList.toggle('bx-x');
    navbar.classList('active')
}

