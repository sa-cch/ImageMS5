package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import dao.ImageDao;
import domain.Image;

/**
 * Servlet implementation class AddImageServlet
 */
@WebServlet("/addImage")
@MultipartConfig(location = "C:/Users/zd1I14/temp")
public class AddImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ImageDao imageDao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport
			.processInjectionBasedOnCurrentContext(this);
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/addImage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// エラーフラグ
		boolean isError = false;

		// パラメータの取得
		// 画像名の取得とバリデーション
		String imageName = request.getParameter("imageName");
		request.setAttribute("imageName", imageName);
		if(imageName == null || imageName.isEmpty()) {
			request.setAttribute("imageNameError", "タイトル：未入力です。");
			isError = true;
		}

		// 施設名の取得とバリデーション
		String facilityName = request.getParameter("facilityName");
		request.setAttribute("facilityName", facilityName);
		if(facilityName == null || facilityName.isEmpty()) {
			request.setAttribute("facilityNameError", "シセツ：未入力です。");
			isError = true;
		}

		// 屋内外種別の取得
		Integer areaTypesId = Integer.parseInt(request.getParameter("areaTypesId"));
		request.setAttribute("areaTypesId", areaTypesId);

		// エリア名の取得
		Integer areaNamesId = Integer.parseInt(request.getParameter("areaNamesId"));
		request.setAttribute("areaNamesId", areaNamesId);

		// 写真の取得
		// uploadsフォルダ内のファイルリストを取得する
		File uploadsDirectory = new File(request.getServletContext().getRealPath("/uploads"));
		// アップロードされたファイルを保存する
		Part part = request.getPart("photo");
		String filename = part.getSubmittedFileName();
		if(part.getSize() > 0) {
			part.write(uploadsDirectory + "/" + filename);
		}
		// ファイル名をリクエストスコープにセットする
		request.setAttribute("filename", filename);
		if(filename == null || filename.isEmpty()) {
			request.setAttribute("photoError", "フォト：未選択です。");
			isError = true;
		}


		// メモの取得とバリデーション
		String memo = request.getParameter("memo");
		request.setAttribute("memo", memo);
		if(memo == null || memo.isEmpty()) {
			request.setAttribute("memoError", "メモ：未入力です。");
			isError = true;
		}


		// エラーがなければデータの追加
		// エラーがあればフォームの再表示
		if(!isError) {
			// データの追加
			Image image = new Image();
			image.setImageName(imageName);
			image.setFacilityName(facilityName);
			image.setAreaTypesId(areaTypesId);
			image.setAreaNamesId(areaNamesId);
			image.setPhoto(filename);
			image.setMemo(memo);
			try {
//				ImageDao imageDao = DaoFactory.createImageDao();
				imageDao.insert(image);
				request.getRequestDispatcher("/WEB-INF/view/addImageDone.jsp").forward(request, response);
			} catch(Exception e) {
				throw new ServletException(e);
			}

		} else {
			// フォームの再表示
			request.getRequestDispatcher("/WEB-INF/view/addImage.jsp").forward(request, response);
		}

	}

	}

