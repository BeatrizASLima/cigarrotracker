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
}
