Documents - MongoDB stores data records as BSON documents. BSON is a binary representation of JSON documents, though it contains more data types than JSON.
Database - A database is list of collections and views stored in mongodb.
Collection - A collection is list of data elements stored in mongodb. (we can compare it with tables in SQL Databases)
View - It is simiar to SQL view, it does not contains any physical storage, instead it is created on top of Collection for horizontal/vertical selection. In views, columns can be added (calculated fields), removed (any fields which are not required), join columns from two or more collections
Materialized View - MongoDB adds the $merge stage for the aggregation pipeline. This stage can merge the pipeline results to an existing collection instead of completely replacing the collection. This functionality allows users to create on-demand materialized views, where the content of the output collection can be updated each time the pipeline is run.
Capped Collections - Capped collections are fixed-size collections that support high-throughput operations that insert and retrieve documents based on insertion order. Capped collections work in a way similar to circular buffers: once a collection fills its allocated space, it makes room for new documents by overwriting the oldest documents in the collection.
Time Series Collections - Time series collections efficiently store sequences of measurements over a period of time. Time series data is any data that is collected over time and is uniquely identified by one or more unchanging parameters. The unchanging parameters that identify your time series data is generally your data source's metadata.


Commaind Groups:
1. Create Commands:
db.createCollection()
db.createView()

2. Collection modification
db.getCollection()
db.getCollectionInfos()
db.getCollectionNames()

3. Selection/Find/Aggregate/Find
db.collection.aggregate()
db.collection.find()
db.collection.findOne()
db.collection.countDocuments()
db.collection.estimatedDocumentCount()
db.collection.count()
db.collection.distinct()

4. Drop Command
db.collection.drop()



5. To select database
use demo

6. Insert records in Collection
db.inventory.insertMany([
   { item: "journal", qty: 25, status: "A", size: { h: 14, w: 21, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "notebook", qty: 50, status: "A", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank" ] },
   { item: "paper", qty: 10, status: "D", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank", "plain" ] },
   { item: "planner", qty: 0, status: "D", size: { h: 22.85, w: 30, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "postcard", qty: 45, status: "A", size: { h: 10, w: 15.25, uom: "cm" }, tags: [ "blue" ] }
]);

6. Select all records
db.inventory.find({})

db.inventory.find({}).pretty()

7. Apply filtering
db.inventory.find( { status: "D" } );
db.inventory.find( { qty: 0 } );
db.inventory.find( { qty: 0, status: "D" } );
db.inventory.find( { size: { h: 14, w: 21, uom: "cm" } } )
db.inventory.find( { tags: "red" } )
db.inventory.find( { tags: [ "red", "blank" ] } )

8. Projection (select only specified fields)
db.inventory.find( { }, { item: 1, status: 1 } );

db.inventory.find( {}, { _id: 0, item: 1, status: 1 } );

9. Create Collection with Validation
db.createCollection( "contacts", {
   validator: { $jsonSchema: {
      bsonType: "object",
      required: [ "phone" ],
      properties: {
         phone: {
            bsonType: "string",
            description: "must be a string and is required"
         },
         email: {
            bsonType : "string",
            pattern : "@mongodb\.com$",
            description: "must be a string and match the regular expression pattern"
         },
         status: {
            enum: [ "Unknown", "Incomplete" ],
            description: "can only be one of the enum values"
         }
      }
   } }
} )

Example:
db.createCollection( "order")

db.order.insertMany([
{"item" : "journal", "price" : NumberDecimal("12.00"), "quantity" : 2 },
{"item" : "postcard", "price" : NumberDecimal("20.00"), "quantity" : 1 },
{"item" : "journal", "price" : NumberDecimal("10.95"), "quantity" : 5 },
{"item" : "paper", "price" : NumberDecimal("5.95"), "quantity" : 5 },
{"item" : "paper", "price" : NumberDecimal("5.95"), "quantity" : 10 }
])

Create View

db.createView(
   "orderView",
   "order",
   [ { $project: { "item": 1, department: 1 } } ]
)

db.createView (
   "orderDetails",
   "order",
   [
     { $lookup: { from: "inventory", localField: "item", foreignField: "item", as: "inventory_docs" } },
     { $project: { "inventory_docs._id": 0, "inventory_docs.item": 0 } }
   ]
);

10. Custom Filters
var documents = new[]
{
    new BsonDocument { { "_id", 1 }, { "item", BsonNull.Value } },
    new BsonDocument { { "_id", 2 } }
};
collection.InsertMany(documents);


var filter = Builders<BsonDocument>.Filter.Eq("item", BsonNull.Value);
var result = collection.Find(filter).ToList();


var filter = Builders<BsonDocument>.Filter.Type("item", BsonType.Null);
var result = collection.Find(filter).ToList();

var filter = Builders<BsonDocument>.Filter.Exists("item", false);
var result = collection.Find(filter).ToList();

11. Aggregation
db.inventory.aggregate([
   { $match: { status: "A" } },
   { $group: { _id: "$item", total: { $sum: "$qty" } } }
])



https://dzone.com/articles/webflux-reactive-programming-with-spring-part-3
https://medium.com/dataseries/public-claims-and-how-to-validate-a-jwt-1d6c81823826
db.person.insertOne({_id: 101, "name": "XMAN", "password": "PWX"})
