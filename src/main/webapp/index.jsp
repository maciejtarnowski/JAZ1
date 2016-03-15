<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="charset" content="utf-8">
    <title>Kalkulator kredytowy</title>
</head>
<body>
<form method="get" action="/credit">
    <p>
        <label>
            Kwota kredytu:
            <input type="text" name="amount" value="1000">
        </label>
    </p>
    <p>
        <label>
            Ilość rat:
            <input type="text" name="installments" value="12">
        </label>
    </p>
    <p>
        <label>
            Oprocentowanie roczne:        
            <input type="text" name="interest_rate" value="5.0">
        </label>
    </p>
    <p>
        <label>
            Opłata stała:
            <input type="text" name="fixed_fee" value="100">
        </label>
    </p>
    <p>
        <label>
            Raty stałe:
            <input type="radio" name="installment_type" value="0" checked="checked">
        </label>
        <label>
            Raty malejące:
            <input type="radio" name="installment_type" value="1">
        </label>
    </p>
    <p>
        <input type="submit" value="Oblicz">
    </p>
</form>
</body>
</html>