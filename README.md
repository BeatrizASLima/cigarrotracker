# CigarroTracker

Aplicativo Android para acompanhar o consumo diario de cigarros, visualizar estatisticas e estimar o gasto ao longo do tempo. Tambem inclui uma tela para buscar locais proximos para comprar tabaco.

## Destaques
- Registro diario com botoes de +1/-1 e fechamento do dia.
- Historico e estatisticas com graficos de evolucao.
- Estimativa de gasto mensal com preco do maco e cigarros por maco.
- Busca de locais por coordenadas ou cidade usando Overpass e Nominatim.
- Persistencia local com Room.

## Telas
- Hoje: contador do dia e total acumulado.
- Estatisticas: grafico de evolucao e media por dia.
- Dinheiro gasto: graficos por mes e configuracao de preco do maco.
- Lugares: lista de pontos para comprar tabaco.

## Tecnologias
- Kotlin + Jetpack Compose
- Navigation Compose
- Room (persistencia local)
- Hilt (injeção de dependencia)
- Retrofit + Gson (consumo de APIs)

## Estrutura do projeto

```
app/
  src/main/
    java/com/example/cigarrotracker/
      MainActivity.kt        # navegacao e bottom bar
      Ecra01.kt              # tela Hoje
      Ecra02.kt              # tela Estatisticas
      Ecra03.kt              # tela Dinheiro gasto
      EcraLugares.kt         # tela Lugares
      CigarroViewModel.kt    # regras de negocio e estado
      PlacesViewModel.kt     # busca de lugares
      data/
        CigarroDatabase.kt   # Room database
        CigarroDao.kt        # DAO
        CigarroRepository.kt # repositorio local
        HistoricoEntity.kt   # entidades do Room
        EstatisticasEntity.kt
        repository/
          PlacesRepository.kt
        service/
          OverpassApiService.kt
          NominatimApiService.kt
      di/
        HiltModule.kt
        RetrofitModule.kt
      ui/theme/
        Color.kt
        Theme.kt
        Type.kt
    res/
      drawable/
      values/
```

## Como executar
1. Abra o projeto no Android Studio.
2. Aguarde o Gradle sincronizar as dependencias.
3. Execute no emulador ou em um dispositivo fisico.

Comando alternativo via terminal:

```bash
./gradlew assembleDebug
```

## Dados e APIs
- Historico e estatisticas sao salvos localmente com Room.
- A tela de lugares consulta o OpenStreetMap via Overpass e usa Nominatim para geocoding.

## Roadmap (ideias)
- Backup/sync em nuvem.
- Exportacao de dados em CSV.
- Meta diaria e notificacoes.

---

Feito com Kotlin e Compose.
