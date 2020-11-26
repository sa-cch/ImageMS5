package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteCheckServlet
 */
@WebServlet("/deleteCheck")
public class DeleteCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("id");
//		System.out.println(strId);
// ↑ここまではid値入ってる。
//		Image image = new Image();
//		image.setId(Integer.parseInt(strId));
//		System.out.println(image.getId());
// ↑ここもid値入ってる。
		HttpSession session = request.getSession();
		session.setAttribute("id", strId);
// ↑セッションスコープに値格納。
		request.getRequestDispatcher("/WEB-INF/view/deleteImage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// なぜか途中、doPostが作動してたけどやっぱりいらんかった！ここは経由してない！
//		String strId = "";
//
//		HttpSession session = request.getSession();
//		strId = (String)session.getAttribute("id");
//		System.out.println(strId);
//
////		if(strId == "") {
////			System.out.println("から");
////		} else {
////		System.out.println(id);
////		}
////		Image image = new Image();
////		image.setId(Integer.parseInt(strId));
//		request.getRequestDispatcher("deleteImage").forward(request, response);
	}

}
