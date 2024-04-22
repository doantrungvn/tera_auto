<%@ tag language="java" pageEncoding="utf-8" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="teraqp" %>

<%@ attribute name="value" required="true"%>

<teraqp:date value="${value}" type="datetime"></teraqp:date>
