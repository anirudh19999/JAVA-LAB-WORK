@WebFilter("/home")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("username") != null) {

            chain.doFilter(request, response);

        } else {

            res.sendRedirect("index.html");
        }
    }
}