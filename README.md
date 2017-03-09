# SERVLET CRUD 
This source code is for learning purpose. Some of the source code are modified from the references. 

## Installation
Map directory for uploading static content in "C"\servers\uploads". Put this configuration inside <Host> in eclipse servers
``<Context docBase="C:\servers\uploads" path="/servlet-crud/images"/>``


### References : 
- [Servlets & JSP - Falkner Jones](http://java.cnam.fr/iagl/biblio/Serlvets%20&%20JSP%20-%20Falkner%20Jones.pdf)
- [The fileupload Example Application](https://docs.oracle.com/javaee/6/tutorial/doc/glraq.html)

### Basic Table 
CREATE TABLE `pikarcms_tablename` (
  `id` int(11) NOT NULL,
  .... another field .... 
  `registerIp` varchar(19) DEFAULT NULL,
  `registerUserId` int(11) NOT NULL,
  `registerDate` datetime DEFAULT NULL,
  `modificationIp` varchar(19) DEFAULT NULL,
  `modificationUserId` int(11) DEFAULT NULL,
  `modificationDate` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pikarcms_tablename`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `pikarcms_tablename`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;