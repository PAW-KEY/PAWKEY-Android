package com.paw.key.presentation.ui.region

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapView
import com.paw.key.core.designsystem.component.CustomSnackBar
import com.paw.key.core.designsystem.component.PawkeyButton
import com.paw.key.core.designsystem.theme.PawKeyTheme
import com.paw.key.core.util.UiState
import com.paw.key.presentation.ui.region.component.regionalMapView
import com.paw.key.presentation.ui.region.state.RegionContract
import com.paw.key.presentation.ui.region.viewmodel.RegionViewModel

@Composable
fun RegionalManagementRoute(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    navigateUp: () -> Unit,
    navigateNext: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Todo : 나중에 서버에서 좌표받아올때 변경
        /*val polyPoints: List<List<LatLng>> = listOf(
            // 첫 번째 폴리곤 (JSON의 첫 번째 MultiPolygon 내부 배열)
            listOf(
                LatLng.from(37.51711061445719, 127.02190765991858),
                LatLng.from(37.5161125807596, 127.01952529624154),
                LatLng.from(37.5162164883837, 127.01949421508716),
                LatLng.from(37.51634601247464, 127.01945553876253),
                LatLng.from(37.516422032983385, 127.01943276459217),
                LatLng.from(37.516493268957156, 127.0194115728236),
                LatLng.from(37.51658759850198, 127.0193834865847),
                LatLng.from(37.5167114910819, 127.01934640344408),
                LatLng.from(37.51681623660209, 127.01931514101004),
                LatLng.from(37.51686945185448, 127.0192990801264),
                LatLng.from(37.516939282166696, 127.01927840809685),
                LatLng.from(37.516972233082456, 127.01926851840392),
                LatLng.from(37.51698462233648, 127.01926481118849),
                LatLng.from(37.517073599708, 127.01923814855776),
                LatLng.from(37.51784201238795, 127.01900855849422),
                LatLng.from(37.51834856560168, 127.01885739486927),
                LatLng.from(37.51852652008926, 127.01880422597102),
                LatLng.from(37.518767826346405, 127.01873199985994),
                LatLng.from(37.519631972731, 127.0184737908733),
                LatLng.from(37.51989834547682, 127.01839431737152),
                LatLng.from(37.52008277814354, 127.01833921339474),
                LatLng.from(37.520172872545615, 127.01831218683317),
                LatLng.from(37.52031647930034, 127.01826944859013),
                LatLng.from(37.52181196916008, 127.01782245481363),
                LatLng.from(37.522899876144514, 127.01996479353504),
                LatLng.from(37.52725966892685, 127.02855140361123),
                LatLng.from(37.52832450596142, 127.03064935800306),
                LatLng.from(37.52839799030839, 127.0307981545756),
                LatLng.from(37.52846832036669, 127.03094943909745),
                LatLng.from(37.52853554125602, 127.03110289480544),
                LatLng.from(37.52856261769042, 127.03116855588503),
                LatLng.from(37.528599698029076, 127.03125847645138),
                LatLng.from(37.5286604753754, 127.03141600288478),
                LatLng.from(37.52866790335819, 127.03143661849312),
                LatLng.from(37.52871800840194, 127.03157560990157),
                LatLng.from(37.52877217106535, 127.03173689015938),
                LatLng.from(37.52879888178338, 127.03182190844049),
                LatLng.from(37.528823377804585, 127.03189988906105),
                LatLng.from(37.52887091685941, 127.032064481855),
                LatLng.from(37.528913897395014, 127.03222622205418),
                LatLng.from(37.52891507654427, 127.03223065732513),
                LatLng.from(37.52895585689404, 127.03239825706638),
                LatLng.from(37.52897212752453, 127.03247216187224),
                LatLng.from(37.52899298770258, 127.0325669076093),
                LatLng.from(37.52901309847874, 127.03266734365153),
                LatLng.from(37.52902700951119, 127.03273680148128),
                LatLng.from(37.5290573727592, 127.0329077461073),
                LatLng.from(37.529084077484455, 127.0330795830779),
                LatLng.from(37.52908815082746, 127.03311010796904),
                LatLng.from(37.52910711467926, 127.03325228973858),
                LatLng.from(37.52911764967627, 127.0333439203287),
                LatLng.from(37.52912706101892, 127.03342570792468),
                LatLng.from(37.52913922466754, 127.03355799914269),
                LatLng.from(37.529143060605016, 127.0335996562364),
                LatLng.from(37.52915371530593, 127.03374694825756),
                LatLng.from(37.52915568106159, 127.03377413489103),
                LatLng.from(37.52916464317443, 127.03394879303299),
                LatLng.from(37.529165893254614, 127.03398996256927),
                LatLng.from(37.52916994689909, 127.03412378902247),
                LatLng.from(37.529170456570625, 127.03417050174527),
                LatLng.from(37.52917187158305, 127.03429897588363),
                LatLng.from(37.529170394485185, 127.03442665682716),
                LatLng.from(37.52916984965598, 127.03447414970417),
                LatLng.from(37.52916545800852, 127.03461663842589),
                LatLng.from(37.52916444878873, 127.03464915232168),
                LatLng.from(37.529163280182395, 127.03467168784088),
                LatLng.from(37.529155389677, 127.03482397227508),
                LatLng.from(37.52914280756699, 127.0349982702012),
                LatLng.from(37.52912663032017, 127.03517226099541),
                LatLng.from(37.52910684900254, 127.03534568442375),
                LatLng.from(37.529100384084096, 127.0353938307457),
                LatLng.from(37.529083679926735, 127.03551829166808),
                LatLng.from(37.5290568798214, 127.03569010522133),
                LatLng.from(37.52902642168969, 127.03586102322775),
                LatLng.from(37.528992584840374, 127.0360310571053),
                LatLng.from(37.52895509012183, 127.03619968629393),
                LatLng.from(37.528946754894946, 127.03623385972809),
                LatLng.from(37.528914207741465, 127.03636723898052),
                LatLng.from(37.52781091773432, 127.04069391721202),
                LatLng.from(37.52762446077623, 127.04062794388285),
                LatLng.from(37.52345671251342, 127.03915391072887),
                LatLng.from(37.52318052479818, 127.03905849923869),
                LatLng.from(37.5229799732422, 127.0384265029062),
                LatLng.from(37.52252343826306, 127.03698785521867),
                LatLng.from(37.52180499371449, 127.03472508312028),
                LatLng.from(37.521086776009405, 127.03246235449895),
                LatLng.from(37.520368523862565, 127.03019966923272),
                LatLng.from(37.519690744419655, 127.0280648314727),
                LatLng.from(37.51963699264117, 127.02793702265375),
                LatLng.from(37.51892867148254, 127.02624620154333),
                LatLng.from(37.51868917897965, 127.02567435052492),
                LatLng.from(37.517741042495466, 127.02341173553359),
                LatLng.from(37.51711061445719, 127.02190765991858) // 닫는 좌표
            ),
            // 두 번째 폴리곤 (JSON의 두 번째 MultiPolygon 내부 배열)
            listOf(
                LatLng.from(37.522605348868, 127.01318773696728),
                LatLng.from(37.522586194511874, 127.01317960045584),
                LatLng.from(37.52258796070942, 127.01317710071716),
                LatLng.from(37.52259182651724, 127.01317164880014),
                LatLng.from(37.52262676328338, 127.01311904079898),
                LatLng.from(37.52265982568028, 127.01306924921995),
                LatLng.from(37.522750776447694, 127.01293229387471),
                LatLng.from(37.522878493009884, 127.01273917804974),
                LatLng.from(37.5229634510042, 127.01261041114844),
                LatLng.from(37.52297621091163, 127.01259106894084),
                LatLng.from(37.523387627731445, 127.011948970083),
                LatLng.from(37.52345362551245, 127.01184775583278),
                LatLng.from(37.52359530778524, 127.01163044094803),
                LatLng.from(37.523746181017174, 127.01139864651478),
                LatLng.from(37.52427043541193, 127.01059531089771),
                LatLng.from(37.52448092304767, 127.01027463651133),
                LatLng.from(37.524769417366876, 127.00983513015997),
                LatLng.from(37.52487976493087, 127.00966695761956),
                LatLng.from(37.524934434013005, 127.00958363471199),
                LatLng.from(37.52518532353923, 127.00920095406983),
                LatLng.from(37.525356952499386, 127.00894351731921),
                LatLng.from(37.52539341918812, 127.00888881270578),
                LatLng.from(37.52559503575671, 127.00857826409263),
                LatLng.from(37.52560105405098, 127.00858413596852),
                LatLng.from(37.525603198294114, 127.00858621771383),
                LatLng.from(37.52577099835366, 127.00875504253904),
                LatLng.from(37.52584955116615, 127.00883495222979),
                LatLng.from(37.52589961627903, 127.00888589856191),
                LatLng.from(37.52591261680786, 127.00889912448108),
                LatLng.from(37.52598779112633, 127.00897371712523),
                LatLng.from(37.526379699442096, 127.00935062113959),
                LatLng.from(37.52647401832642, 127.00944431283939),
                LatLng.from(37.526613095480016, 127.00958309141903),
                LatLng.from(37.52689914167713, 127.0098652327395),
                LatLng.from(37.52694710744606, 127.00991017306919),
                LatLng.from(37.526985577350864, 127.00994619778814),
                LatLng.from(37.52700302845431, 127.00996246776681),
                LatLng.from(37.52701147918971, 127.00997077242218) // 닫는 좌표
            )
        )*/

        viewModel.getRegionGeometry(
            X_USER_ID = 2,
            regionId = 35,
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is RegionContract.RegionSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(
                        sideEffect.message
                    )

                    RegionContract.RegionSideEffect.NavigateNext -> navigateNext()
                    RegionContract.RegionSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    when (state.uiState) {
        is UiState.Success -> {
            val mapView = regionalMapView(
                lifeCycle = lifecycleOwner.lifecycle,
                context = context,
                currentUserLocation = state.centerLocation,
                polyPoints = (state.uiState as UiState.Success<List<List<LatLng>>>).data
            )

            RegionalManagementScreen(
                paddingValues = paddingValues,
                snackBarHostState = snackBarHostState,
                mapView = mapView,
                selectedRegion = state.selectedRegion,
                onClickButton = {
                    viewModel.onChangeRegion()
                },
                modifier = modifier
            )
        }

        is UiState.Loading -> {

        }

        else -> {}
    }
}

@Composable
fun RegionalManagementScreen(
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
    mapView: MapView,
    selectedRegion : String?,
    onClickButton : () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .padding(paddingValues),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = {
                    CustomSnackBar(
                        data = it,
                        modifier = Modifier
                            .padding(bottom = LocalConfiguration.current.screenHeightDp.dp * 0.4f + 16.dp)
                    )
                }
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            AndroidView(
                factory = { mapView },
                modifier = Modifier
                    .align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp)
                    )
                    .background(
                        color = PawKeyTheme.colors.white1
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "선택한 위치",
                            style = PawKeyTheme.typography.head20B2,
                            color = PawKeyTheme.colors.black
                        )
                        
                        Text(
                            text = selectedRegion ?: "강남구 역삼동",
                            style = PawKeyTheme.typography.head20B2,
                            color = PawKeyTheme.colors.green500
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "기존에 산책하던 지역은 0000이에요.\n선택한 위치로 산책 지역을 변경하시겠어요?",
                        style = PawKeyTheme.typography.body14M,
                        color = PawKeyTheme.colors.gray500,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PawkeyButton(
                        text = "지역 변경하기",
                        onClick = {
                            onClickButton()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = true,
                    )
                }
            }
        }
    }
}

