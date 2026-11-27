<h1 align="center">BoArt</h1>

<p align="center">
  <img src="src/main/webapp/img/palette.svg" width="72" height="72" alt="BoArt">
</p>

<p align="center">
  Сайт-галерея для художников: авторы выкладывают работы и собирают их в подборки,<br>
  заказчики находят исполнителя по работам, а не по описанию.
</p>

<p align="center">
  <em>Учебный проект по курсу «Основы разработки информационных систем»</em><br>
  Сервлеты без веб-фреймворков · Java 25 · JSP + JSTL · PostgreSQL · Maven
</p>

<hr>

<h2>Стек</h2>

<table>
  <tr><td><b>Язык</b></td><td>Java 25</td></tr>
  <tr><td><b>Сервер</b></td><td>Apache Tomcat 11 (Jakarta EE 10)</td></tr>
  <tr><td><b>Шаблоны</b></td><td>JSP + JSTL 3.0, без Java-кода в шаблонах</td></tr>
  <tr><td><b>База</b></td><td>PostgreSQL 17</td></tr>
  <tr><td><b>Доступ к данным</b></td><td>JDBC, <code>PreparedStatement</code></td></tr>
  <tr><td><b>Сборка</b></td><td>Maven, <code>packaging war</code></td></tr>
  <tr><td><b>Прочее</b></td><td>jBCrypt для паролей, свой CSS и JS, без фреймворков</td></tr>
</table>

<p>
  Из сторонних библиотек в проекте только драйвер PostgreSQL, JSTL и jBCrypt.
  Вёрстка написана руками, Bootstrap и подобное не используются.
</p>

<h2>Что умеет</h2>

<ul>
  <li>регистрация, вход, выход, роли <code>USER</code> и <code>ADMIN</code>;</li>
  <li>работы: создание с загрузкой изображения, правка, удаление, публикация;
      черновик видит только автор, опубликованное – все;</li>
  <li>галереи: подборки работ автора, до шести на аккаунт, публикация на главной
      и в поиске исполнителя;</li>
  <li>теги: общие выбираются галочками, свои вводятся вручную;</li>
  <li>лайки через AJAX, подписки на авторов, счётчики подписчиков;</li>
  <li>поиск по названию, описанию, тегу и имени автора;</li>
  <li>пагинация на главной и в профиле;</li>
  <li>админка: блокировка пользователей.</li>
</ul>

<blockquote>
  Ограничения: три черновика и шесть галерей на аккаунт, до десяти тегов на работу
  (из них не больше трёх своих). Пустую галерею нельзя выставить на главную.
</blockquote>

<h2>Структура</h2>

<pre><code>src/main/java/ru/student/gallery/
├── config/      DbConfig, ServiceRegistry
├── dao/         интерфейсы и Jdbc*-реализации
├── service/     бизнес-логика
├── servlet/     контроллеры
├── filter/      Encoding, Auth, FormSecurity, ErrorLog
├── listener/    AppContextListener – собирает сервисы при старте
├── model/       User, Artwork, Gallery, Tag, SessionUser, Role
├── exception/   NotFoundException, AccessDeniedException
└── util/        PasswordHasher, ImageUtil, Tokens, Pager

src/main/webapp/
├── WEB-INF/jsp/ шаблоны (header, footer, страницы)
├── css/ js/ img/ fonts/
└── uploads/     загруженные работы и аватары</code></pre>

<h2>Запуск</h2>

<h3>1. База</h3>

<pre><code>createdb -U postgres gallery_db
psql -U postgres -d gallery_db -f sql/schema.sql
psql -U postgres -d gallery_db -f sql/data.sql</code></pre>

<p><code>data.sql</code> содержит демо-данные: пользователей, работы, галереи, теги и связи.</p>

<h3>2. Конфиг</h3>

<p>Создать <code>src/main/resources/db.properties</code> – в репозиторий он не попадает:</p>

<pre><code>db.url=jdbc:postgresql://localhost:5432/gallery_db
db.user=postgres
db.password=ваш_пароль
db.pool.size=10</code></pre>

<h3>3. Сборка и деплой</h3>

<pre><code>mvn clean package</code></pre>

<p>
  В IntelliJ IDEA удобнее Smart Tomcat: deployment directory – <code>src/main/webapp</code>,
  context path — <code>/gallary</code>. Tomcat 9 не подойдёт, проект на <code>jakarta.*</code>.
</p>

<p>Адрес: <code>http://localhost:8080/gallary/</code></p>

<h3>4. Администратор</h3>

<pre><code>логин:  admin
пароль: admin12345</code></pre>

<h2>Тесты</h2>

<pre><code>mvn test</code></pre>

<p>
  23 теста: сервисы бизнес-логики на заглушках DAO и DAO на реальной базе.
  Для тестов нужна отдельная база <code>gallery_test</code> со схемой из <code>schema.sql</code>:
</p>

<pre><code>createdb -U postgres gallery_test
psql -U postgres -d gallery_test -f sql/schema.sql</code></pre>

<p>
  Тестовый конфиг лежит в <code>src/test/resources/db.properties</code> и подхватывается
  вместо основного. Тесты DAO чистят таблицы перед каждым методом, поэтому направлять
  их на рабочую базу нельзя.
</p>

<h2>Заметки</h2>

<ul>
  <li>изображения при загрузке перекодируются: JPEG сжимается с качеством 0.85,
      сторона уменьшается до 1600 px, аватары — до 400 px;</li>
  <li>формы защищены CSRF-токеном и одноразовым токеном отправки
      (<code>FormSecurityFilter</code>);</li>
  <li>пароли хранятся в виде BCrypt-хеша;</li>
  <li>все SQL-запросы идут через <code>PreparedStatement</code>.</li>
</ul>

<hr>

<h2>Авторы</h2>

<p>
  ananasik-developer – разработка, дизайн, архитектура приложения.<br>
  В предустановленной базе – демонстрационные работы от авторов: @benneakxsys, @koshmarikk1, @bytheway31, @Werngral
</p>
