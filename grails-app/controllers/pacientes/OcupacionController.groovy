package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OcupacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ocupacion.list(params), model:[ocupacionCount: Ocupacion.count()]
    }

    def show(Ocupacion ocupacion) {
        respond ocupacion
    }

    def create() {
        respond new Ocupacion(params)
    }

    @Transactional
    def save(Ocupacion ocupacion) {
        if (ocupacion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ocupacion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ocupacion.errors, view:'create'
            return
        }

        ocupacion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ocupacion.label', default: 'Ocupacion'), ocupacion.id])
                redirect ocupacion
            }
            '*' { respond ocupacion, [status: CREATED] }
        }
    }

    def edit(Ocupacion ocupacion) {
        respond ocupacion
    }

    @Transactional
    def update(Ocupacion ocupacion) {
        if (ocupacion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ocupacion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ocupacion.errors, view:'edit'
            return
        }

        ocupacion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ocupacion.label', default: 'Ocupacion'), ocupacion.id])
                redirect ocupacion
            }
            '*'{ respond ocupacion, [status: OK] }
        }
    }

    @Transactional
    def delete(Ocupacion ocupacion) {

        if (ocupacion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ocupacion.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ocupacion.label', default: 'Ocupacion'), ocupacion.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ocupacion.label', default: 'Ocupacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
