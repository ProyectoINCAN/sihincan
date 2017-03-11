package pacientes

class CorreoElectronico {

    String id
    String descripcion
    Boolean habilitado = true

    static constraints = {
        id size: 1..3, blank: false, unique:true
        descripcion size: 1..70, blank: false
    }
}
