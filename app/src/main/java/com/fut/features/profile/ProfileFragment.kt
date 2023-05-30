package com.fut.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import com.fut.R
import com.fut.core.ui.theme.Poppins
import com.fut.core.utils.SnackBarType
import com.fut.core.utils.showSnackWith
import com.fut.databinding.FragmentProfileBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy
                    .DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                SetupFragment()
            }
        }
    }

    @Composable
    private fun SetupFragment() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_background))
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            SetTitle(R.string.profile_title)
            Spacer(modifier = Modifier.height(16.dp))
            SetProfileCard()
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(colorResource(id = R.color.dark_container))
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_team_color_accent_32),
                        contentDescription = null,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    SetNormalText(text = "Gerenciar time do coração", modifier = Modifier.weight(1f))

                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24_white),
                        contentDescription = null,
                    )
                }
            }
        }
    }

    @Composable
    private fun SetNormalText(text: String, modifier: Modifier) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            modifier = modifier
        )
    }

    @Composable
    private fun SetProfileCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(colorResource(id = R.color.dark_container))
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (edit) = createRefs()

                IconButton(
                    onClick = {
                        showSnackWith(
                            "Clicou em editar perfil",
                            SnackBarType.Success
                        )
                    },
                    modifier = Modifier.constrainAs(edit) {
                        end.linkTo(parent.end)
                    }
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit_white_white_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.colorAccent))
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        shape = RoundedCornerShape(40.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(1.dp),
                            colors = CardDefaults.cardColors(Color.White),
                            shape = RoundedCornerShape(40.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.diniz),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Fernando Diniz",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "fernandodiniz@contato.com",
                        color = colorResource(id = R.color.neutral1)
                    )
                }
            }
        }
    }

    @Composable
    private fun SetTitle(title: Int) {
        Text(
            text = stringResource(id = title),
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.poppins_black))
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun SetupFragmentPreview() {
        SetupFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}