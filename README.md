# game-ex-changer
## Currency exchange application

Simple app for two way exchange PLN and USD currencies based on NBP Web API
(http://api.nbp.pl/)

# Getting started

## Register account
POST
```
http://localhost:8080/api/account/register
```
Body
```json
{
	"firstName": "John",
	"lastName": "Smith",
	"pesel": "52062688779",
	"initialFunds": "1000"
}
```

Please keep in mind that PESEL neets to be correct.
I recomend using pesel generator like [this](https://pesel.cstudios.pl/o-generatorze/generator-on-line)
## Exchange currency
POST
```
http://localhost:8080/api/exchange
```
BODY
```json
{
	"currencyFrom": "PLN",
	"currencyTo": "USD",
	"amount": "50",
	"accountPesel": "52062688779"
}
```
Avalable country currency codes for `currencyFrom` and `currencyTo` are:
* PLN
* USD

## Get account info
GET
```
http://localhost:8080/api/account/info/{pesel}
```
