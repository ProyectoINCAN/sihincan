package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EtniasController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Etnias.list(params), model:[etniasCount: Etnias.count()]
    }

    def show(Etnias etnias) {
        respond etnias
    }

    def create() {
        respond new Etnias(params)
    }

    @Transactional
    def save(Etnias etnias) {
        if (etnias == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (etnias.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond etnias.errors, view:'create'
            return
        }

        etnias.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'etnias.label', default: 'Etnias'), etnias.id])
                redirect etnias
            }
            '*' { respond etnias, [status: CREATED] }
        }
    }

    def edit(Etnias etnias) {
        respond etnias
    }

    @Transactional
    def update(Etnias etnias) {
        if (etnias == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (etnias.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond etnias.errors, view:'edit'
            return
        }

        etnias.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'etnias.label', default: 'Etnias'), etnias.id])
                redirect etnias
            }
            '*'{ respond etnias, [status: OK] }
        }
    }

    @Transactional
    def delete(Etnias etnias) {

        if (etnias == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        etnias.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'etnias.label', default: 'Etnias'), etnias.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'etnias.label', default: 'Etnias'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
