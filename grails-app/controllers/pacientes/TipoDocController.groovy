package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TipoDocController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TipoDoc.list(params), model:[tipoDocCount: TipoDoc.count()]
    }

    def show(TipoDoc tipoDoc) {
        respond tipoDoc
    }

    def create() {
        respond new TipoDoc(params)
    }

    @Transactional
    def save(TipoDoc tipoDoc) {
        if (tipoDoc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoDoc.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDoc.errors, view:'create'
            return
        }

        tipoDoc.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tipoDoc.label', default: 'TipoDoc'), tipoDoc.id])
                redirect tipoDoc
            }
            '*' { respond tipoDoc, [status: CREATED] }
        }
    }

    def edit(TipoDoc tipoDoc) {
        respond tipoDoc
    }

    @Transactional
    def update(TipoDoc tipoDoc) {
        if (tipoDoc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tipoDoc.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDoc.errors, view:'edit'
            return
        }

        tipoDoc.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tipoDoc.label', default: 'TipoDoc'), tipoDoc.id])
                redirect tipoDoc
            }
            '*'{ respond tipoDoc, [status: OK] }
        }
    }

    @Transactional
    def delete(TipoDoc tipoDoc) {

        if (tipoDoc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tipoDoc.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tipoDoc.label', default: 'TipoDoc'), tipoDoc.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tipoDoc.label', default: 'TipoDoc'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
