package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EtniaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Etnia.list(params), model:[etniaCount: Etnia.count()]
    }

    def show(Etnia etnia) {
        respond etnia
    }

    def create() {
        respond new Etnia(params)
    }

    @Transactional
    def save(Etnia etnia) {
        if (etnia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (etnia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond etnia.errors, view:'create'
            return
        }

        etnia.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'etnia.label', default: 'Etnia'), etnia.id])
                redirect etnia
            }
            '*' { respond etnia, [status: CREATED] }
        }
    }

    def edit(Etnia etnia) {
        respond etnia
    }

    @Transactional
    def update(Etnia etnia) {
        if (etnia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (etnia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond etnia.errors, view:'edit'
            return
        }

        etnia.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'etnia.label', default: 'Etnia'), etnia.id])
                redirect etnia
            }
            '*'{ respond etnia, [status: OK] }
        }
    }

    @Transactional
    def delete(Etnia etnia) {

        if (etnia == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        etnia.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'etnia.label', default: 'Etnia'), etnia.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'etnia.label', default: 'Etnia'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
