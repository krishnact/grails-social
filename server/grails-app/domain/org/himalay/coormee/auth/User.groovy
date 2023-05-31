package org.himalay.coormee.auth;

import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	SpringSecurityService springSecurityService

	String username
	String password

	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired


	Set<Role> getAuthorities() {
		(UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		password nullable: true, blank: true, password: true
		username nullable: false, blank: false, unique: true
		oAuthIDs minSize: 0

	}

	static mapping = {
		password column: '`password`'
	}
	static hasMany = [oAuthIDs: OAuthID]


}