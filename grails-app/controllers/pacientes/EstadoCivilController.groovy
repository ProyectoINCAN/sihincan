package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EstadoCivilController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EstadoCivil.list(params), model:[estadoCivilCount: EstadoCivil.count()]
    }

    def show(EstadoCivil estadoCivil) {
        respond estadoCivil
    }

    def create() {
        respond new EstadoCivil(params)
    }

    @Transactional
    def save(EstadoCivil estadoCivil) {
        if (estadoCivil == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estadoCivil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estadoCivil.errors, view:'create'
            return
        }

        estadoCivil.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estadoCivil.label', default: 'EstadoCivil'), estadoCivil.id])
                redirect estadoCivil
            }
            '*' { respond estadoCivil, [status: CREATED] }
        }
    }

    def edit(EstadoCivil estadoCivil) {
        respond estadoCivil
    }

    @Transactional
    def update(EstadoCivil estadoCivil) {
        if (estadoCivil == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estadoCivil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estadoCivil.errors, view:'edit'
            return
        }

        estadoCivil.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'estadoCivil.label', default: 'EstadoCivil'), estadoCivil.id])
                redirect estadoCivil
            }
            '*'{ respond estadoCivil, [status: OK] }
        }
    }

    @Transactional
    def delete(EstadoCivil estadoCivil) {

        if (estadoCivil == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        estadoCivil.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'estadoCivil.label', default: 'EstadoCivil'), estadoCivil.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoCivil.label', default: 'EstadoCivil'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
