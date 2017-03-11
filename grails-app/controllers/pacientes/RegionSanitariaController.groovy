package pacientes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RegionSanitariaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond RegionSanitaria.list(params), model:[regionSanitariaCount: RegionSanitaria.count()]
    }

    def show(RegionSanitaria regionSanitaria) {
        respond regionSanitaria
    }

    def create() {
        respond new RegionSanitaria(params)
    }

    @Transactional
    def save(RegionSanitaria regionSanitaria) {
        if (regionSanitaria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (regionSanitaria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond regionSanitaria.errors, view:'create'
            return
        }

        regionSanitaria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'regionSanitaria.label', default: 'RegionSanitaria'), regionSanitaria.id])
                redirect regionSanitaria
            }
            '*' { respond regionSanitaria, [status: CREATED] }
        }
    }

    def edit(RegionSanitaria regionSanitaria) {
        respond regionSanitaria
    }

    @Transactional
    def update(RegionSanitaria regionSanitaria) {
        if (regionSanitaria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (regionSanitaria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond regionSanitaria.errors, view:'edit'
            return
        }

        regionSanitaria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'regionSanitaria.label', default: 'RegionSanitaria'), regionSanitaria.id])
                redirect regionSanitaria
            }
            '*'{ respond regionSanitaria, [status: OK] }
        }
    }

    @Transactional
    def delete(RegionSanitaria regionSanitaria) {

        if (regionSanitaria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        regionSanitaria.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'regionSanitaria.label', default: 'RegionSanitaria'), regionSanitaria.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'regionSanitaria.label', default: 'RegionSanitaria'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
