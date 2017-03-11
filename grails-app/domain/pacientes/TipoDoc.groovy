package pacientes

class TipoDoc {

    String codigo
    String nombre
    Boolean habilitado = true

    static constraints = {
        codigo size: 1..3, blank: false, unique:true
        nombre size: 1..70, blank: false
        //habilitado defaultValue:true, blank:false
        //habilitado attributes:[defaultValue:true], blank:false
    }
}
