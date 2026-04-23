
import service.MovieServiceImpl;
import utils.InputUtil;
import utils.ViewUtil;

//    run:  chcp 65001; ./gradlew clean build; java -jar .\build\libs\miniProject-movie-search-1.0-SNAPSHOT.jar

public class App {
    public static void main(String[] args) {
        MovieServiceImpl movieService = new MovieServiceImpl();

        do {
            ViewUtil.printTitle();
            String title = InputUtil.getString(" [+] Enter movie title : ");
            Integer page = 1;
            boolean isTrue = true;
            do {
                Integer totalPage = movieService.searchMovies(title, page);
                ViewUtil.paginationMenu();

                String op = InputUtil.getString(" [+] Input option : ");

                switch (op.toLowerCase()) {
                    case "n" -> {
                        if (!page.equals(totalPage)) {
                            page++;
                        } else {
                            System.out.println(" (!) Already on the last page!\n");
                        }
                    }
                    case "p" -> {
                        if (!page.equals(1)) {
                            page--;
                        } else {
                            System.out.println(" (!) Already on the first page!\n");
                        }
                    }
                    case "g" -> {
                        while (true) {
                            page = InputUtil.getInteger(" [-] Input page number (1 -> " + totalPage + ") : ");
                            if (page > totalPage || page < 1) {
                                System.out.println(" -> (!) The page must be from 1 to " + totalPage);
                                continue;
                            }
                            break;
                        }
                    }
                    case "md" -> {
                        movieService.movieDetail(InputUtil.getInteger(" [-] Input movie ID : "));
                        InputUtil.pressEnterToContinue();
                    }
                    case "b" -> isTrue = false;
                    case "e" -> {
                        System.out.println(" -> Exit the program..!");
                        System.exit(0);
                    }
                    default -> System.out.println(" -> (!) Invalid option..!");
                }
            } while (isTrue);
        } while (true);

    }
}
