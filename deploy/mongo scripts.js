



db.createRole(
    {
        role: "USER",
        privileges: [
            { resource: { cluster: true }, actions: [ "addShard" ] },
            { resource: { db: "config", collection: "" }, actions: [ "find", "update", "insert", "remove" ] },
            { resource: { db: "users", collection: "usersCollection" }, actions: [ "update", "insert", "remove" ] },
            { resource: { db: "", collection: "" }, actions: [ "find" ] }
        ],
        roles: [
            { role: "read", db: "admin" }
        ]
    },
    { w: "majority" , wtimeout: 5000 }
)




db.createUser({
    user: "user",
    pwd: "123",
    roles: [
        { role: "USER", db: "admin" }
    ]
})

db.getUsers( {showCredentials: true} )