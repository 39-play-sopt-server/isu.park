package org.sopt;

import org.sopt.Controller.PostController;
import org.sopt.View.PostView;

public class Main {
    public static void main(String[] args) {
        PostView view = new PostView();
        PostController controller = new PostController(view);
        controller.run();
    }
}