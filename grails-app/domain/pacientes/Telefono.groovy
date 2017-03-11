package pacientes

class Telefono {

    String id
    String numero
    Boolean habilitado = true

    static constraints = {
        id size: 1..3, blank: false, unique:true
        numero size: 1..70, blank: false
    }
}
