let menulcon = document.querySelector('#menu-icon');
let navbar = document.querySelector('.navbar');
let sections = document.querySelectorAll('section');
let navLinks =  document.querySelectorAll('header nav a');


window.onscroll = () => {
    sections.forEach(sec =>  {
        let top = window.scrollY;
        let offset = sec.offsetTop - 150;
        let height = sec.offsetHeight;
        let id = sec.getAttribute('id');
    
        if(top >= offset && top < offset + height){
            navLinks.forEach(links => {
                links.classList.remove('active');
                document.querySelector('header nav a[href*=' + id + ']').classList.add('active');
            })
        }
    })
}
menulcon.onclick = () => {
    menulcon.classList.toggle('bx-x');
    navbar.classList.toggle('active');
}

const themeToggleBtn = document.getElementById('theme-toggle');
const body = document.body;
const themeIcon = themeToggleBtn.querySelector('i');

themeToggleBtn.addEventListener('click', () => {
    body.classList.toggle('light-mode');
    if(body.classList.contains('light-mode')) {
        themeIcon.classList.replace('bx-moon', 'bx-sun');
    } else {
        themeIcon.classList.replace('bx-sun', 'bx-moon');
    }
});

// Lógica de envio do formulário de contato para a API Spring Boot
const contactForm = document.getElementById('contact-form');
if (contactForm) {
    contactForm.addEventListener('submit', async (e) => {
        e.preventDefault(); // Evita o recarregamento padrão da página

        const submitBtn = contactForm.querySelector('input[type="submit"]');
        const originalBtnText = submitBtn.value;
        submitBtn.value = 'Enviando...';
        submitBtn.disabled = true;

        try {
            // Cria um objeto FormData contendo todos os dados e o anexo
            const formData = new FormData(contactForm);

            // Realiza a requisição POST para a API (ajuste a porta se o seu Spring rodar em outra diferente da 8080)
            const response = await fetch('http://localhost:8080/api/contato', {
                method: 'POST',
                body: formData, // FormData formata automaticamente como multipart/form-data
            });

            if (response.ok) {
                const responseData = await response.text();
                alert('Mensagem enviada com sucesso!\n' + responseData);
                contactForm.reset(); // Limpa o formulário após o sucesso
            } else {
                const errorData = await response.text();
                alert('Erro ao enviar mensagem:\n' + errorData);
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
            alert('Não foi possível conectar ao servidor. Verifique se o backend está rodando.');
        } finally {
            // Restaura o botão original
            submitBtn.value = originalBtnText;
            submitBtn.disabled = false;
        }
    });
}

