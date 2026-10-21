# Manual verification checklist

After MySQL and Tomcat are configured, verify these interactions in a browser:

1. Open the product collection; confirm the six seed products load and search filters by name or brand.
2. Add a product to the bag, change its quantity, remove it, and confirm empty-cart messaging.
3. Register an account, sign in, and sign out. Duplicate email and invalid input should show a message.
4. Attempt checkout while signed out; the filter should redirect to the account page.
5. Sign in, place a mock order, then open Orders and confirm the order is listed.
6. Set the account role to `ADMIN`; confirm `/admin/products` loads, a product can be added, and stock can be changed.
7. Sign in as a customer and open `/admin/products`; the server should return 403.
8. Start `InventoryServer`, run `InventoryClient`, and confirm the stock response.
9. Start `InventoryServerMain`, run `InventoryClientMain`, and confirm the RMI lookup response.
10. Launch `AdminDemo`, add a row, select a row, and remove it.

The provided project uses real MySQL and a local Tomcat instance, so those services must be running for the web checks. The sample desktop, network, and RMI modules run independently.
