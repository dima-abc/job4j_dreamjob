<h1>Проект "Работа мечты"</h1>

В этом блоке мы поговорим о создании веб приложений на языка Java.<br>

Мы будем использовать Spring boot, как основную библиотеку,
но рассматривать детали этого фреймворка не будем.
Это будет в блоке Spring. <br>

Основная задача этого блока, понять архитектуру веб приложений. <br>

Мы будем разрабатывать приложение "Работа мечты". <br>

В системе будут две модели: вакансии и кандидаты.
Кандидаты будут публиковать резюме.
Кадровики будут публиковать вакансии о работе. <br>

Кандидаты могут откликнуться на вакансию.
Кадровик может пригласить на вакансию кандидата. <br>
<table>
<thead>
<tr>
    <th>Ссылка на вид</th>
    <th>Класс.имя метода обработчика</th>
</tr>
</thead>
<tbody>
<tr>
<td>http://localhost:8080/index</td>
<td>IndexControl.index()</td>
</tr>
<tr>
<td>http://localhost:8080/vacanise</td>
<td>VacancyController.getAllVacancy(Model model)</td>
</tr>
<tr>
<td>http://localhost:8080/vacanise/create</td>
<td>VacancyController.getCreationPageVacancy()</td>
</tr>
<tr>
<td>http://localhost:8080/candidates</td>
<td>CandidateController.getAllCandidate(Model model)</td>
</tr>
<tr>
<td>http://localhost:8080/candidates/create</td>
<td>CandidateController.getCreationPageCandidate()</td>
</tr>
</tbody>
</table>