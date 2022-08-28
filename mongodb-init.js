db.createUser(
    {
        user: "rizwan",
        pwd: "secret",
        roles: [
            {
                role: "readWrite",
                db: "scheduler"
            }
        ]
    }
);

db = db.getSiblingDB("scheduler");

db.createCollection("locks");

db.locks.createIndex({"acquiredAt": 1}, {"expireAfterSeconds": 30});