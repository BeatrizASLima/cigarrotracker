package com.example.cigarrotracker.data

class CigarroRepository(
    private val dao: CigarroDao
) {

    suspend fun carregarEstatisticas(): EstatisticasEntity? {
        return dao.getEstatisticas()
    }

    suspend fun salvarEstatisticas(cigarrosTotal: Int, diasDeUso: Int) {
        val est = EstatisticasEntity(
            id = 1,
            cigarrosTotal = cigarrosTotal,
            diasDeUso = diasDeUso
        )
        dao.salvarEstatisticas(est)
    }

    suspend fun carregarHistorico(limit: Int): List<HistoricoEntity> {
        return dao.getHistorico(limit)
    }

    suspend fun carregarHistoricoPorDia(diaIndex: Int): HistoricoEntity? {
        return dao.getHistoricoPorDia(diaIndex)
    }

    suspend fun salvarHistorico(diaIndex: Int, cigarros: Int) {
        val est = HistoricoEntity(
            diaIndex = diaIndex,
            cigarros = cigarros
        )
        dao.salvarHistorico(est)
    }
}
