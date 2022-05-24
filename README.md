# Vikadata&trade; Java SDK (*vika.java*)<br />Java Client Library for the Vika OpenAPI

![vika.java](https://socialify.git.ci/vikadata/vika.java/image?description=1&descriptionEditable=Vika%20is%20a%20API-based%20SaaS%20database%20platform%20for%20users%20and%20developers%2C%20Java%20SDK%20for%20connecting%20vikadata%20Open%20API.&font=Inter&forks=1&issues=1&language=1&logo=http%3A%2F%2Fs1.vika.ltd%2Fdatasheet%2Flogo.png&owner=1&pattern=Charlie%20Brown&stargazers=1&theme=Dark)

[![MIT](https://img.shields.io/badge/licenses-MIT-blue)](https://vikadata.mit-license.org/)
[![Maven Central](https://img.shields.io/maven-central/v/cn.vika/vika-client.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22cn.vika%22%20AND%20a:%22vika-client%22)
[![Build](https://www.travis-ci.com/vikadata/vika.java.svg?branch=master)](https://www.travis-ci.com/github/vikadata/vika.java)
[![javadoc](https://javadoc.io/badge2/cn.vika/vika-client/javadoc.svg)](https://javadoc.io/doc/cn.vika/vika-client)

[github_issues]:https://github.com/vikadata/vika.java/issues
[github_issues_new]:https://github.com/vikadata/vika.java/issues/new

[Vika](https://vika.cn) Java SDK

Vikadata&trade; Java API (*vika.java*) provides a full featured and easy to consume Java
library for working with vikadata via the Vikadata OpenAPI.<br/>

---

## Usage

### Java Version Requirement

Java 8+ is required to use sdk. not support Java 8 below

## Getting Started

### Installation

* Maven

```xml

<dependency>
    <groupId>cn.vika</groupId>
    <artifactId>vika-client</artifactId>
    <version>1.0.2</version>
</dependency>
```

* Gradle

```groovy
dependencies {
  ... ...
  implementation('cn.vika:vika-client:1.0.2')
}
```

## **Usage Example**

vika java sdk is quite simple to use, you don't need to set api url, all you need is the
``Personal Api Key`` from your vika account settings page. Once you have that info it is as
simple as:

First, you need to set api credential which belong your personal api key.

```java
ApiCredential credential = new ApiCredential("Your API Key");
```

Then, Init client instance

```java
VikaApiClient vikaApiClient = new VikaApiClient(credential);
```

By default, the API client has been added for setting connect and read timeouts, you can also change:

```java
// Set the connect timeout to 8 second and the read timeout to 9 seconds
VikaApiClient vikaApiClient = new VikaApiClient(credential)
        .withRequestTimeout(80000)
        .withReadTimeout(90000);
```

As private deployment user, you can also change host url

```java
VikaApiClient vikaApiClient = new VikaApiClient("http://ip:port", credential);
```

#### **Query Record**

Most simple usage for query record quickly

```java
// Get 10 records on first page
List<Record> records = vikaApiClient.getRecordApi().getRecords("datasheetId", 1, 10);
```

#### **Pager Resulting**

API client provides an easy way to use paging mechanism to page through lists of results from the Open API.
Below code are a couple of examples on how to use the Pager:

```java
// Get a Pager instance that will page through the records with 100 record per page
Pager<Record> pager = vikaApiClient.getRecordApi().getRecords("datasheet_id", 100);

// Iterate through the pages and print out the per record detail
while (pager.hasNext()) {
    for (Record record : pager.next()) {
        System.out.println(record.getRecordId() + " -: " + record.getFields());
    }
}
```

you can also fetch all the items as a single list using a Pager instance:
```java
// Get a Pager instance so we can load all the records into a single list, 100 record at a time:
Pager<Record> pager = vikaApiClient.getRecordApi().getRecords("datasheet_id", 100);
List<Record> records = pager.all();
```

**Java 8 Stream Support**

also provide method that returns a Java 8 Stream.
```java
// Pager as stream,support forEach、Group、Filter operation
Stream<Record> records = vikaApiClient.getRecordApi().getRecordsAsStream("datasheet_id");

// ex: extract record id to a list
records.map(Record::getRecordId).collect(Collectors.toList());
```

**Advance Query**

```java
// build query condition
ApiQueryParam queryParam = new ApiQueryParam(1, 50)
            .withView("viewId")
            .withFields(Arrays.asList("fieldName"))
            .withRecordIds(Arrays.asList("recordId"))
            .withSort("fieldName", Order.DESC).withSort("fieldName", Order.ASC)
            .withFilter("{fieldName}>1");
// query return pager result
Pager<Record> pager = vikaApiClient.getRecordApi().getRecords("datasheet_id", queryParam);
```

#### **Add Record**

Class ``RecordMap`` is a key-value structure like ``Map<String, Object>``, all thing you do is converting json to map, you can use convert util from sdk provide which is named ``JacksonConverter``, you also can use jackson api build json structure data, more detail please reference unit test.

you can add record through two difference way, `id` or `name`, default is `name` fieldKey.

using default fieldKey `name` example:
```java
// Build Record Map by jackson api
ObjectNode fieldMap = JsonNodeFactory.instance.objectNode()
        // simple data
        .put("fieldName", "string")
        .put("number", 1234);
        // sub tree node
        .set("city", JsonNodeFactory.instance.arrayNode().add("NewYork").add("Bejing"));
// put record map into fields key
ObjectNode fields = JsonNodeFactory.instance.objectNode().set("fields", fieldMap);
// only one record, warp record into array node
ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode().add(fields);
// convert json to Map List
List<RecordMap> recordMaps = JacksonConverter.unmarshalToList(RecordMap.class, arrayNode);
// create record request
CreateRecordRequest recordRequest = new CreateRecordRequest()
    .withRecords(recordMaps);
// ok
List<Record> newRecords = vikaApiClient.getRecordApi().addRecords("datasheet_id", recordRequest);
```

using fieldKey `id` example:
```java
// Build Record Map by jackson api
ObjectNode fieldMap = JsonNodeFactory.instance.objectNode()
        // simple data
        .put("fld_id", "string")
        .put("fld_id", 1234);
        // sub tree node
        .set("fld_id", JsonNodeFactory.instance.arrayNode().add("NewYork").add("Bejing"));
// put record map into fields key
ObjectNode fields = JsonNodeFactory.instance.objectNode().set("fields", fieldMap);
// only one record, warp record into array node
ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode().add(fields);
// convert json to Map List
List<RecordMap> recordMaps = JacksonConverter.unmarshalToList(RecordMap.class, arrayNode);
// create record request
CreateRecordRequest recordRequest = new CreateRecordRequest()
    .withRecords(recordMaps)
    .withFieldKey(FieldKey.ID);
// ok
List<Record> newRecords = vikaApiClient.getRecordApi().addRecords("datasheet_id", recordRequest);
```

#### **Update Record**

update record also provide two difference way to modifying data, using fieldKey `id` or `name`, default is `name` fieldKey.

using default fieldKey `name` example:

```java
// Build update record model
UpdateRecord record = new UpdateRecord()
                // row record id from query result or add record result
                .withRecordId("recXXXXX")
                // single-text type field cell
                .withField("SingleText", "ABC")
                // single-select type field cell,
                // it can be set null or empty array if you want to clear field value: withField("Options", null)
                .withField("Options", Arrays.asList("LL", "NN"));
// new Request model
UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
    .withRecords(Collections.singletonList(record));
// request send
List<Record> updateRecords = vikaApiClient.getRecordApi().updateRecords("datasheet_id", updateRecordRequest);
```

using fieldKey `id` example:

```java
// Build update record model
UpdateRecord record = new UpdateRecord()
                // row record id from query result or add record result
                .withRecordId("recXXXXX")
                // single-text type field cell
                .withField("fld_id", "ABC")
                // single-select type field cell,
                // it can be set null or empty array if you want to clear field value: withField("Options", null)
                .withField("fld_id", Arrays.asList("LL", "NN"));
// new Request model
UpdateRecordRequest updateRecordRequest = new UpdateRecordRequest()
    .withRecords(Collections.singletonList(record))
    .withFieldKey(FieldKey.ID);
// request send
List<Record> updateRecords = vikaApiClient.getRecordApi().updateRecords("datasheet_id", updateRecordRequest);
```

#### **Delete Record**

```java
// DELETE one record only
vikaApiClient.getRecordApi().deleteRecord("datasheet_id", "recXXXXXX");

// DELETE many record
vikaApiClient.getRecordApi().deleteRecords("datasheet_id", Arrays.asList("recXXXXXX", "recXXXXXX"));

// Delete all records, may be slowly work if sheet have large records
vikaApiClient.getRecordApi().deleteAllRecords("datasheet_id");
```

#### **Upload Attachment**

sdk provide several way to upload attachment, You can choose the way to upload anything that suits you

```java
// classPath resource on src/main/resource/test.txt
ResourceLoader classPathResource = new ClassPathResourceLoader("test.txt");
Attachment attachment = vikaApiClient.getAttachmentApi().upload("datasheet_id", classPathResource);

// or url resource from web
ResourceLoader urlResource = new UrlResourceLoader(UrlUtil.url("https://test.com/image.png"))
Attachment attachment = vikaApiClient.getAttachmentApi().upload("datasheet_id", urlResource);

// or file resource
File file = new File("/Users/Document/test.txt");
Attachment attachment = vikaApiClient.getAttachmentApi().upload("datasheet_id", new FileResourceLoader(file));

// or upload file type directly
File file = new File("/Users/Document/test.txt");
Attachment attachment = vikaApiClient.getAttachmentApi().upload("datasheet_id", file);
```

#### **Add Field**

You can create field by sdk. Firstly, we need to build the property required by the field. Secondly, Using `CreateFieldRequestBuilder` to creating a `CreateFieldRequest` object. Finally, submitting the request to the specified space and the specified datasheet. If creating the field successfully, we can get the new field's `id` and `name`.

> more detail about field type and property of field see official [API manual#create-field](https://vika.cn/developers/api/reference/#operation/create-fields)

create single text field example:

```java
// build the SingleText field's property
SingleTextFieldProperty singleTextFieldProperty = new SingleTextFieldProperty();
singleTextFieldProperty.setDefaultValue("defaultValue");
// create a CreateFieldRequest Object
CreateFieldRequest<SingleTextFieldProperty> createFieldRequest = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.SingleText)
                .withName("singleText")
                .withProperty(singleTextFieldProperty)
                .build();
// request to create a field
CreateFieldResponse response = vikaApiClient.getFieldApi().addField("space_id", "datasheet_id", createFieldRequest);
```

 create text field example:

```java
// if field don't require property, we can skip the process that build the property
CreateFieldRequest<EmptyProperty> createFieldRequest = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.Text)
                .withName("text")
                .withoutProperty()
                .build();
// request to create a field
CreateFieldResponse createFieldRequest = vikaApiClient.getFieldApi().addField("space_id", "datasheet_id", createFieldRequest);
```

#### **Detele Field**

```java
// we can use field_id to detele field
vikaApiClient.getFieldApi().deleteField("space_id", "datasheet_id", "field_id");
```

#### **Add Datasheet**

You can create a datasheet with the help of a `CreateDatasheetRequest` Object.  If creating the datasheet successfully, we can get the new datasheet's `id`, `name` and the fields' `id`, `name`. 

> more detail see official [API munual#create-datasheet](https://vika.cn/developers/api/reference/#operation/create-datasheets)

```java
// create a CreateDatasheetRequest Object
CreateDatasheetRequest createDatasheetRequest = new CreateDatasheetRequest();
// datasheet's name is required.
request.setName("datasheet");
// add description to datasheet
request.setDescription("description");
// specify the folder where the datasheet is stored
request.setFolderId("fold_id");
// specify the datasheet's previous node
request.setPreNodeId("pre_node_id");

// datasheet's initial fields
SingleTextFieldProperty property = new SingleTextFieldProperty();
property.setDefaultValue("defaultValue");
// a SingleText field
CreateFieldRequest<SingleTextFieldProperty> singleSelectField = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.SingleText)
                .withName("singleSelect")
                .withProperty(property)
                .build();
// a Text field
CreateFieldRequest<EmptyProperty> textField = CreateFieldRequestBuilder
                .create()
                .ofType(FieldTypeEnum.Text)
                .withName("text")
                .withoutProperty()
                .build();
List<CreateFieldRequest<?>> fields = new ArrayList<>();
fields.add(singleSelectField);
fields.add(textField);
request.setFields(fields);

// request to create a datasheet
CreateDatasheetResponse response = vikaApiClient.getDatasheetApi().addDatasheet("space_id", createDatasheetRequest);
```

## Reporting Issues

Vika java sdk project uses GitHub's integrated issue tracking system to record bugs and feature requests.
If you want to raise an issue, please follow the recommendations below:

* Before you log a bug, please search the [issue tracker][github_issues] to see if someone has already reported the problem.
* If the issue doesn't already exist, [create a new issue][github_issues_new].
* Please provide as much information as possible with the issue report, we like to know the version that you are using, as well as your Operating System and JVM version.
* If you need to paste code, or include a stack trace use Markdown escapes before and after your text.

## License
Open Source software released under the [MIT License](https://vikadata.mit-license.org).

