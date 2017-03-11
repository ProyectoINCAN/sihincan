package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TipoTelefonoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TipoTelefono.list(params), model:[tipoTelefonoCount: TipoTelefono.count()]
    }

    def show(TipoTelefono tipoTelefono) {
        respond tipoTelefono
    }

    def create() {
        respond new TipoTelefono(params)
    }

    @Transactional
    def save(TipoTelefono tipoTelefono) {
        if (tipoTelefono == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoTelefono.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoTelefono.errors, view:'create'
            return
        }

        tipoTelefono.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tipoTelefono.label', default: 'TipoTelefono'), tipoTelefono.id])
                redirect tipoTelefono
            }
            '*' { respond tipoTelefono, [status: CREATED] }
        }
    }

    def edit(TipoTelefono tipoTelefono) {
        respond tipoTelefono
    }

    @Transactional
    def update(TipoTelefono tipoTelefono) {
        if (tipoTelefono == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoTelefono.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoTelefono.errors, view:'edit'
            return
        }

        tipoTelefono.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoTelefono.label', default: 'TipoTelefono'), tipoTelefono.id])
                redirect tipoTelefono
            }
            '*'{ respond tipoTelefono, [status: OK] }
        }
    }

    @Transactional
    def delete(TipoTelefono tipoTelefono) {

        if (tipoTelefono == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tipoTelefono.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoTelefono.label', default: 'TipoTelefono'), tipoTelefono.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoTelefono.label', default: 'TipoTelefono'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
