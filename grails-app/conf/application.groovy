/**
 * Created by fabian on 13/03/2017.
 */

grails.gorm.default.mapping = {
    cache true
    id generator: 'sequence'
    'user-type'(type: org.hibernate.type.YesNoType, class: Boolean)
}