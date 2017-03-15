package pacientes

class Profesion {
    String nombre
    Boolean habilitado = true

    static constraints = {
        id size: 1..3, blank: false, unique:true
        nombre size: 1..70, blank: false
    }
}
