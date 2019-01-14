const fs = require('fs');

createUser = (id) =>{
return `{
        "id":  1,
        "name": "Grego Le YO",
        "phones": {
        "home": "800-123-4567",
        "mobile": "877-123-1234"
        },
        "email": [
        "jd@example.com",
        "jd@example.org"
        ],
        "dateOfBirth": "1980-01-02T00:00:00.000Z",
        "registered": true,
        "emergencyContacts": [
        {
            "name": "Jane Doe",
            "phone": "888-555-1212",
            "relationship": "spouse"
        },
        {
            "name": "Justin Doe",
            "phone": "877-123-1212",
            "relationship": "parent"
        }
        ]
}`

}
const writeStream = fs.createWriteStream('users.json');
const users = [];

const MAX = 1
;
writeStream.write('[');
for(let i=0;i < MAX; i++){
    const u = createUser(i);
    writeStream.write(u);
    if(i < MAX-1)
        writeStream.write(',');

}
writeStream.write(']');
writeStream.end();

