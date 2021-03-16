# Vikadata&trade; Java SDK (*vika.java*)<br />Java Client Library for the Vikadata OpenAPI

[![LGPL-2.1](https://img.shields.io/badge/License-LGPL--2.1-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/vikadata/vika-sdk-java/badge.svg)](https://search.maven.org/artifact/cn.vika/vika-client)
[![Build](https://www.travis-ci.com/vikadata/vika.java.svg?branch=master)](https://www.travis-ci.com/github/vikadata/vika.java)
[![JavaDoc](https://javadoc.io/badge2/cn.vika/vika.java/javadoc.io.svg)](https://javadoc.io/doc/cn.vika/vika-client)

[github_issues]:https://github.com/vikadata/vika.java/issues
[github_issues_new]:https://github.com/vikadata/vika.java/issues/new

[简体中文](./README_zh.md) | English

[Vika](https://vika.cn) Official Java SDK

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
    <version>1.0.1</version>
</dependency>
```

* Gradle

```groovy
dependencies {
  ... ...
  implementation('cn.vika:vika-client:1.0.1')
}
```

## **Usage Example**

vika java client is quite simple to use, you don't need to set api url, all you need is the
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
while (pager.hasNext())) {
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

## Reporting Issues
Vika java sdk project uses GitHub's integrated issue tracking system to record bugs and feature requests.
If you want to raise an issue, please follow the recommendations below:

* Before you log a bug, please search the [issue tracker][github_issues] to see if someone has already reported the problem.
* If the issue doesn't already exist, [create a new issue][github_issues_new].
* Please provide as much information as possible with the issue report, we like to know the version that you are using, as well as your Operating System and JVM version.
* If you need to paste code, or include a stack trace use Markdown escapes before and after your text.

## License
Open Source software released under the [LGPL-2.1](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt).


