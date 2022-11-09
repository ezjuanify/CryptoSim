# Aquariux Test 09/11/2022

## Description
1. Application will fetch from BTCUSDT and ETHUSDT from Binance and Huobi every 10 seconds.
2. Highest aggregated price will be selected.
3. User is then able to view the selected price and proceed to trade (buy/sell).
4. User can then view their balance.
5. Users can retrieve their trading history (transactions).

## Technical Details
Application is hosted on port 8080.

## Technology

- Spring Boot
- Hibernate
- H2
- RESTful

## Endpoints
# /latest
Returns the latest selected highest aggregated price.

# /{id}/{currency}/{opt}/{amt}
Allows trading of currency.
- id - ID of user
- currency - BTC/ETH
- opt - Buy/Sell
- amt - Amount to be traded

# /{id}/balance
Allows user to view their balance.
- id - ID of user

# /{id}/transactions
Allows user to view their transactions.
- id - ID of user