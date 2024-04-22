<h2><strong>Contents</strong></h2>

<p>&nbsp; &nbsp;<a href="#prerequisite"> 1. Pre-requisite</a></p>

<p>&nbsp; &nbsp;<a href="#stepbystep"> 2. Initialize database step by step</a></p>

<h2><a id="prerequisite" name="prerequisite">1. Pre-requisite:</a></h2>

<p>&nbsp; &nbsp; This guideline is based on some already installed softwares:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; + Oracle (installed in a database host)</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; + Sqldeveloper</p>

<p>&nbsp; &nbsp; You can download the software on &quot;Oracle downloads&quot; <a target="_blank" href="https://www.oracle.com/downloads/index.html">website</a></p>

<p>&nbsp;</p>

<h2><a id="stepbystep" name="stepbystep">2. Initialize&nbsp;database step by step:</a></h2>

<p>&nbsp; &nbsp; 2.1 Create database</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Open Sqldeveloper:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/oracle/1.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Click button&nbsp;<img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/oracle/2.png" />&nbsp; to open &quot;New server registration&quot; popup :</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Fill out information according to your [Database settings]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Fill [System user] information and click button connect to connect System user (choice <b>Role = SYSDBA</b>)</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/oracle/3.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Run script to create user your project</p>
<blockquote>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;CREATE USER &#60;your project db user&#62; IDENTIFIED BY &#60;your project db pass&#62;;</p>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;GRANT RESOURCE TO &#60;your project db user&#62;;</p>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;GRANT CONNECT TO &#60;your project db user&#62;;</p>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;GRANT CREATE VIEW TO &#60;your project db user&#62;;</p>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;GRANT CREATE SESSION TO &#60;your project db user&#62;;</p>
	<p>&nbsp; &nbsp; &nbsp; &nbsp;GRANT UNLIMITED TABLESPACE TO &#60;your project db user&#62;;</p>
</blockquote>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Create new connection &#38; access with user, who was created in previous step (choice <b>Role = default</b>)</p>

<p>2.2 Run DDL script:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Copy ddl script you has generated in [Generate DDL Scripts] screen and paste to SQL editor</p>

<p><img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/oracle/7.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Press F5 to execute script</p>

<p>&nbsp;</p>

<p>&nbsp;</p>
