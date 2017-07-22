function SomenteNumero(e)
{
    var key;
    var keychar;
    var reg;
    
    if(window.event) {
        //o for IE, e.keyCode or window.event.keyCode podem ser usados
        key = event.keyCode;
    }
    else if(e.which) {
        // Firefox e Netscape
        key = e.which;
    }
    else {
        return true;
    }
    
    keychar = String.fromCharCode(key);
    reg = /\d/;  // checa se contem caracteres alfa

    if (key != 8) // No Firefox ignora a tecla BACKSPACE
        return reg.test(keychar);   // retorna somente numericos
}
function DeixarSomenteNumeros(componente) {
	var val = componente.value;
	var newVal = "";
	var reg = /\d/;
	var i;
	for (i = 0; i < val.length; i++) {
		var ch = val.charAt(i);
		if (reg.test(ch)) {
			newVal = newVal + ch;
		}
	}
	componente.value = newVal;
}