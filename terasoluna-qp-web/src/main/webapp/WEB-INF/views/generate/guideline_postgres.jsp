<h2><strong>Contents</strong></h2>

<p>&nbsp; &nbsp;<a href="#prerequisite"> 1. Pre-requisite</a></p>

<p>&nbsp; &nbsp;<a href="#stepbystep"> 2. Initialize database step by step</a></p>

<h2><a id="prerequisite" name="prerequisite">1. Pre-requisite:</a></h2>

<p>&nbsp; &nbsp; This guideline is based on some already installed softwares:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; + PostgresSQL (installed in a database host)</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; + PgAdmin</p>

<p>&nbsp; &nbsp; You can download the software on &quot;The PostgreSQL Global Development Group&quot; <a target="_blank" href="http://www.postgresql.org/">website</a></p>

<p>&nbsp;</p>

<h2><a id="stepbystep" name="stepbystep">2. Initialize&nbsp;database step by step:</a></h2>

<p>&nbsp; &nbsp; 2.1 Create database</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Open PgAdmin:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/1.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Click button&nbsp;<img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/2.png" style="height:24px; width:24px" />&nbsp; to open &quot;New server registration&quot; popup&quot; :</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/3.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; Fill out information according to your [Database settings]:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Name&quot; - (required)</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Host&quot; - [Database host name]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Port&quot; - [Database port]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Maintenance DB&quot; - postgres</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Username&quot; - [Database user]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; &quot;Password&quot; - [Database password]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; --&gt; Press [OK]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - A database connection&#39;s created as image below:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/4.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Right click on &quot;Database&quot; -&gt; select &quot;New Database&quot;:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/5.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; <img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/6.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; Fill out &quot;Name&quot; as [Database name] in your [Database settings]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; --&gt; Press [OK]</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; --&gt; Database created</p>

<p>2.2 Run DDL script:</p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Copy ddl script you has generated in [Generate DDL Scripts] screen and paste to SQL editor</p>

<p><img alt="" src="${pageContext.request.contextPath}/resources/media/images/guideline/postgres/7.png" style="width:500px" /></p>

<p>&nbsp; &nbsp; &nbsp; &nbsp; - Press F5 to execute script</p>

<p>&nbsp;</p>

<p>&nbsp;</p>
