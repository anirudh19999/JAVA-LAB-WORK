# Database relationships

```mermaid
erDiagram
  USERS ||--o{ ORDERS : places
  ORDERS ||--|{ ORDER_ITEMS : contains
  PRODUCTS ||--o{ ORDER_ITEMS : appears_in
  CATEGORIES o|--o{ PRODUCTS : groups
```

The demo schema keeps the first version focused on accounts, categories, products, orders, and order items. Addresses, wishlist, reviews, coupons, and payment provider integration are natural extensions described by the supplied project guide.
