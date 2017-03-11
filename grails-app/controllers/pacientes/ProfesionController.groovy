package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProfesionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Profesion.list(params), model:[profesionCount: Profesion.count()]
    }

    def show(Profesion profesion) {
        respond profesion
    }

    def create() {
        respond new Profesion(params)
    }

    @Transactional
    def save(Profesion profesion) {
        if (profesion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (profesion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profesion.errors, view:'create'
            return
        }

        profesion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'profesion.label', default: 'Profesion'), profesion.id])
                redirect profesion
            }
            '*' { respond profesion, [status: CREATED] }
        }
    }

    def edit(Profesion profesion) {
        respond profesion
    }

    @Transactional
    def update(Profesion profesion) {
        if (profesion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (profesion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profesion.errors, view:'edit'
            return
        }

        profesion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'profesion.label', default: 'Profesion'), profesion.id])
                redirect profesion
            }
            '*'{ respond profesion, [status: OK] }
        }
    }

    @Transactional
    def delete(Profesion profesion) {

        if (profesion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        profesion.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'profesion.label', default: 'Profesion'), profesion.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'profesion.label', default: 'Profesion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
