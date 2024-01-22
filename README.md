# SecureBankPro

SecureBankPro - A security-focused full-stack app developed during a Computer Science Academic Course.

## Tech stack

- JavaScript v16.16.0
- TypeScript v5.1.6
- React v18.2.0
- Java v17
- Spring Boot v3.2.0
- Spring Security v6.2.0
- Spring Data Jpa v3.2.1
- Spring Data Redis v3.2.0
- Spring Session v3.2.0
- redis v7.2.0
- mysql v8.0.33
- modelMapper v3.2.0
- nbvcxz v1.5.1
- bouncycastle v1.70
- lombok
- docker
- nginx v1.24

Explanation of less-known libraries:

- nbvcxz - responsible for veryfing passwords and for couting password Entropy
- bouncycastle - implementation of cryptographic algorithms in java

## Architecture

<img src="documentation/Architecture.drawio.svg"/>

Explanation

- nginx - proxy server
- redis - responsible for caching sessions
- mysql - main storage

## Algohritms

As a algorithm for hashing passwords, I used Argon2 algoritm, beacuse its very secure, slow hashing algorithm.

## Security methods

- stick data validation
- max 5x you can try to login in to given account
- Session
- Argon2 as password hasher
- Entropy counting
- Nginx
- CSRF
- CSP, XSS header
- honeypots
- data encryption for private data
  
## Features
- login & logout
- reset-password
- transfers
