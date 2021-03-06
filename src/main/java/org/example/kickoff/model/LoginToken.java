package org.example.kickoff.model;

import static java.time.temporal.ChronoUnit.MONTHS;
import static javax.persistence.EnumType.STRING;
import static org.hibernate.annotations.CacheConcurrencyStrategy.TRANSACTIONAL;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Cache;
import org.omnifaces.persistence.model.BaseEntity;

@Entity
public class LoginToken extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	public enum TokenType {
		REMEMBER_ME,
		API,
		RESET_PASSWORD
	}

	@Column(length = 32, unique = true)
	private byte[] tokenHash;

	@Column
	private Instant created;

	@Column
	private Instant expiration;

	@Column(length = 45)
	private String ipAddress;

	@Column
	private String description;

	@ManyToOne(optional = false)
	@Cache(usage = TRANSACTIONAL)
	private User user;

	@Enumerated(STRING)
	private TokenType type;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(byte[] tokenHash) {
		this.tokenHash = tokenHash;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getExpiration() {
		return expiration;
	}

	public void setExpiration(Instant expiration) {
		this.expiration = expiration;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	@PrePersist
	public void setTimestamps() {
		created = Instant.now();

		if (expiration == null) {
			expiration = created.plus(1, MONTHS);
		}
	}

}