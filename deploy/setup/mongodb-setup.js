db.createUser(
    {
        user: "root",
        pwd: "12345678",
        roles: [
            { role: "dbOwner", db: "test_db" }
        ]
    })