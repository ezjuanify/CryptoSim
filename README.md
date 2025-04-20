# CryptoSim

## Description
A lightweight backend simulation for crypto trading using Spring Boot.
Fetches real-time BTC/ETH prices from Binance and Huobi, and lets users simulate trades based on the highest market price.

Originally built for a technical interview - now adapted for demo and educational purposes.

1. Application will fetch from BTCUSDT and ETHUSDT from Binance and Huobi every 10 seconds.
2. Highest aggregated price will be selected.
3. User is then able to view the selected price and proceed to trade (buy/sell).
4. User can then view their balance.
5. Users can retrieve their trading history (transactions).

## Technical Details
- Application is hosted on port 8080
- Default ID is 1 (e.g. http://localhost:8080/1/balance)
- You can add more user by inserting into database via /src/main/resources/data.sql

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
- currency - btc/eth
- opt - buy/sell/b/s
- amt - Amount to be traded in double format

# /{id}/balance
Allows user to view their balance.
- id - ID of user

# /{id}/transactions
Allows user to view their transactions.
- id - ID of user