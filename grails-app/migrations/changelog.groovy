databaseChangeLog = {

	changeSet(author: "wolff (generated)", id: "1366706914629-1") {
		createTable(tableName: "attachment") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "attachmentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "file", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "mime_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "page_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-2") {
		createTable(tableName: "authority") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "authorityPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-3") {
		createTable(tableName: "page") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pagePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "body", type: "longvarchar") {
				constraints(nullable: "false")
			}

			column(name: "creator_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "original_page_id", type: "bigint")

			column(name: "state", type: "integer")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-4") {
		createTable(tableName: "persistent_logins") {
			column(name: "series", type: "varchar(64)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "persistent_loPK")
			}

			column(name: "last_used", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "token", type: "varchar(64)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(64)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-5") {
		createTable(tableName: "person") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)")

			column(name: "enabled", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "fullname", type: "varchar(255)")

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "boolean") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-6") {
		createTable(tableName: "person_authority") {
			column(name: "authority_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "person_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-7") {
		createTable(tableName: "space") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "spacePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-8") {
		createTable(tableName: "version") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "versionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "author_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "body", type: "longvarchar") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "number", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "page_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-9") {
		addPrimaryKey(columnNames: "authority_id, person_id", constraintName: "person_authorPK", tableName: "person_authority")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-17") {
		createIndex(indexName: "authority_uniq_1366706914513", tableName: "authority", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-18") {
		createIndex(indexName: "title_idx", tableName: "page") {
			column(name: "title")
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-19") {
		createIndex(indexName: "username_uniq_1366706914527", tableName: "person", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-10") {
		addForeignKeyConstraint(baseColumnNames: "page_id", baseTableName: "attachment", constraintName: "FK8AF75923311F1845", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "page", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-11") {
		addForeignKeyConstraint(baseColumnNames: "creator_id", baseTableName: "page", constraintName: "FK34628F8384E38E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-12") {
		addForeignKeyConstraint(baseColumnNames: "original_page_id", baseTableName: "page", constraintName: "FK34628F6926E7D7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "page", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-13") {
		addForeignKeyConstraint(baseColumnNames: "authority_id", baseTableName: "person_authority", constraintName: "FK2C8236D9F94FFCCF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "authority", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-14") {
		addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_authority", constraintName: "FK2C8236D964288BC5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-15") {
		addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "version", constraintName: "FK14F51CD88941D1CF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "wolff (generated)", id: "1366706914629-16") {
		addForeignKeyConstraint(baseColumnNames: "page_id", baseTableName: "version", constraintName: "FK14F51CD8311F1845", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "page", referencesUniqueColumn: "false")
	}
}
