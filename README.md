# Vikadata&trade; Java SDK (*vika.js*)<br />Java Client Library for the Vikadata OpenAPI

[![LGPL-2.1](https://img.shields.io/badge/License-LGPL--2.1-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/vikadata/vika-sdk-java/badge.svg)](https://search.maven.org/artifact/cn.vika/vika-client)
[![Build](https://www.travis-ci.com/vikadata/vika.java.svg?branch=master)](https://www.travis-ci.com/github/vikadata/vika.java)
[![JavaDoc](https://javadoc.io/badge2/cn.vika/vika.java/javadoc.io.svg)](https://javadoc.io/doc/cn.vika/vika.java)

[简体中文](./README.md) | English

[Vika](https://vika.cn) official Java SDK

Vikadata&trade; Java API (*vika.js*) provides a full featured and easy to consume Java library for working with vikadata via the Vikadata OpenAPI.<br/>

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
    <version>0.1.0</version>
</dependency>
```

* Gradle 
```groovy
dependencies {
  ......
  implementation('cn.vika:vika-client:0.1.0')
}
```

## Usage

vika java client is quite simple to use, you don't need set api url,
all you need is the Personal Api Key from your vika Account Settings page.  Once you have that info it is as simple as:

First, you need to set api credential which belong your personal api key.
```java
ApiCredential credential = new ApiCredential("Your API Key");
```

Then, Init client instance
```java
VikaApiClient vikaApiClient = new VikaApiClient(credential);
```





