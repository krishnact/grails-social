package org.himalay.coormee.auth;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@GrailsCompileStatic
@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {
	public static final String ROLE_ADMIN  = 'ROLE_ADMIN'
	public static final String ROLE_USER   = 'ROLE_USER'

	private static final long serialVersionUID = 1

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE )
	Long id;

	String authority

	static constraints = {
		authority nullable: false, blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}