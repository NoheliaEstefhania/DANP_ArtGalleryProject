package com.example.danp_artgallery.beacon.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver
import com.lemmingapex.trilateration.TrilaterationFunction
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer

class BeaconConfig {
    companion object {
        val allowedUUIDs = listOf(
            "2f234454cf6d4a0fadf2f4911ba9ffb6",
            "2f234454cf6d4a0fadf2f4911ba9ffa0",
            "2f234454cf6d4a0fadf2f4911ba9ffb7"
            // Añade más UUIDs aquí
        )
        const val ROOM_LENGTH = 600.0
        const val ROOM_HEIGHT = 600.0
        val positions = arrayOf(
            doubleArrayOf(0.0, ROOM_LENGTH),
            doubleArrayOf(0.0, 0.0),
            doubleArrayOf(ROOM_HEIGHT, 0.0)
        )
        fun trilaterate(
            positions: Array<DoubleArray>,
            distance: DoubleArray,
            pointXPosition: MutableState<Double>,
            pointYPosition: MutableState<Double>,
            trilateration: MutableState<String>
        ) {
            Log.d("trilaterations","pass")
            val trilaterationFunction = TrilaterationFunction(positions, distance)
            val nolineal = NonLinearLeastSquaresSolver(trilaterationFunction, LevenbergMarquardtOptimizer())
            //trilateracion nolineal
            val nolinealSolve =  nolineal.solve()

            val input = nolinealSolve.point.toString()
            val numbers = input.substring(1, input.length - 1) // Elimina las llaves
                .split("[; ]".toRegex()) // Divide por punto y coma o espacio
//
            //actualizacion de posicion de la "persona"
            pointXPosition.value = (ROOM_LENGTH + numbers[0].toDouble()-700) /** FACTOR_X*/
            pointYPosition.value = (ROOM_HEIGHT - numbers[2].toDouble()-200) /** FACTOR_Y*/

            //imprimir valores de prueba
            trilateration.value = " trilateracion no lineal " + nolinealSolve.point + "\n" +
                    "posicionCanvasX: " + "%.${2}f".format(pointXPosition.value).toDouble() + "\n" +
                    "posicionCanvasY: " + "%.${2}f".format(pointYPosition.value).toDouble()
        }
    }
}